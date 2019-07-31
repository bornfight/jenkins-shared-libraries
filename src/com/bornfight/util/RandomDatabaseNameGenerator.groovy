package com.bornfight.util

class RandomDatabaseNameGenerator implements Serializable {
    private String databaseName

    RandomDatabaseNameGenerator(String databaseName){
        this.databaseName = databaseName
    }

    String generate() {
        Random random = new Random()
        int number = random.nextInt(Integer.MAX_VALUE)

        return databaseName + "_" + number
    }

    void setDatabaseName(String databaseName){
        this.databaseName = databaseName
    }
}
