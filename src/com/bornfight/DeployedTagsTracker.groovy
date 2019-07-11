package com.bornfight

@Grab(group = 'com.google.api-client', module = 'google-api-client', version = '1.23.0')
@Grab(group = 'com.google.apis', module = 'google-api-services-sheets', version = 'v4-rev581-1.25.0')
@Grab(group = 'com.google.oauth-client', module = 'google-oauth-client-jetty', version = '1.23.0')
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.JsonFactory
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.sheets.v4.Sheets
import com.google.api.services.sheets.v4.SheetsScopes
import com.google.api.services.sheets.v4.model.UpdateValuesResponse
import com.google.api.services.sheets.v4.model.ValueRange

class DeployedTagsTracker implements Serializable{

    private final String APPLICATION_NAME = "DeployedTagsTracker";
    private final String CREDENTIALS_FILE_PATH = "credentials.json";
    private final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    private def getCredentials(final NetHttpTransport HTTP_TRANSPORT, String credentials) throws IOException {
        // Load client secrets.
        InputStream is = new ByteArrayInputStream(credentials.getBytes())
        return GoogleCredential.fromStream(is).createScoped(Collections.singleton(SheetsScopes.SPREADSHEETS))
    }

    def update(String sheetId, String credentials, String name, String stage, String tag){
        if(isUndefined(tag)) return

        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport()
        final String range = "DeploymentTracker!A2:C"
        int stageIndex = stage == "staging" ? 1 : 2

        Sheets service = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT, credentials.trim()))
                .setApplicationName(APPLICATION_NAME)
                .build();
        ValueRange currentState = service.spreadsheets().values().get(sheetId, range).execute()

        ValueRange newState = currentState.clone()

        prepareState(currentState)
        List<Object> projectRow = findRow(name, currentState)

        if(projectRow == null){
            projectRow = createNewRow()
            projectRow.set(0, name)
            currentState.getValues().add(projectRow)
        }
        projectRow.set(stageIndex, tag);
        newState.setValues(currentState.getValues())

        ValueRange body = new ValueRange().setValues(newState.getValues())

        service
            .spreadsheets()
            .values()
            .update(sheetId, range, body)
            .setValueInputOption("RAW")
            .execute()
    }

    static boolean isUndefined(String var) {
        return var == null || var.trim().isEmpty() || var.trim() == "null"
    }

    private static void prepareState(ValueRange state){
        if(state.getValues() == null){
            List<List<Object>> row = new ArrayList<>(1)
            row.add(new ArrayList<>())
            state.setValues(row)
        }
        for(List<Object> row: state.getValues()){
            for(int i = row.size(); i <3; i++){
                row.add("");
            }
        }
    }

    private static List<Object> findRow(String name, ValueRange state){
        for(List<Object> row: state.getValues()){
            if(row.get(0).equals(name)){
                return row;
            }
        }
        return null;
    }

    private static List<Object> createNewRow(){
        List<Object> projectRow = new ArrayList<>(3);
        for(int i = 0; i<3; i++){
            projectRow.add("");
        }
        return projectRow;
    }

}
