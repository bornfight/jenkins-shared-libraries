package com.bornfight

@Grab(group='com.google.api-client', module='google-api-client', version='1.30.1')
@Grab(group='com.google.apis', module='google-api-services-sheets', version='v4-rev581-1.25.0')

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets
import com.google.api.services.sheets.v4.SheetsScopes
import com.google.api.services.sheets.v4.model.UpdateValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;

class DeployedTagsTracker implements Serializable{

    private final String APPLICATION_NAME = "DeployedTagsTracker";
    private final String CREDENTIALS_FILE_PATH = "credentials.json";
    private final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    private def getCredentials(final NetHttpTransport HTTP_TRANSPORT, String credentials) throws IOException {
        // Load client secrets.
        echo credentials
        InputStream is = new ByteArrayInputStream(credentials.getBytes())
        if (is == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }
        return GoogleCredential.fromStream(is).createScoped(Collections.singleton(SheetsScopes.SPREADSHEETS));
    }

    def update(String sheetId, String project, String stage, String tag){
        System.println(project)
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        final String range = "DeploymentTracker!A2:C";
        Sheets service = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT, project))
                .setApplicationName(APPLICATION_NAME)
                .build();

        ValueRange currentState = service.spreadsheets().values().get(sheetId, range).execute();
        System.out.println("Project Staging Production");
        for(List<Object> row: currentState.getValues()){
            for(Object cell: row){
                System.out.print(cell+" ");
            }
            System.out.println();
        }
        List<List<Object>> values = Arrays.asList(
                Arrays.asList(
                        "Test1234", "Test123"
                )
        );
        ValueRange body = new ValueRange()
                .setValues(values);
        UpdateValuesResponse result =
                service.spreadsheets().values().update(sheetId, range, body)
                        .setValueInputOption("RAW")
                        .execute();
        System.out.printf("%d cells updated.", result.getUpdatedCells());
        ValueRange response = service.spreadsheets().values()
                .get(sheetId, range)
                .execute();

    }

}
