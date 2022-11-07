package com.function;

import com.microsoft.azure.functions.annotation.*;
import com.microsoft.azure.functions.*;

/**
 * Azure Functions in Java with Cosmos DB Trigger.
 */
public class Function {

    @FunctionName("cosmosDBMonitor")
    public void cosmosDbProcessor(
            @CosmosDBTrigger(name = "items",
            databaseName = "ToDoList", collectionName = "Items",
            createLeaseCollectionIfNotExists = true,
            connectionStringSetting = "AzureCosmosDBConnection") String[] items,
            final ExecutionContext context, @BlobOutput(name = "target", path = "pdl-cosmosdb-poc/{rand-guid}", dataType = "", 
            connection = "AzureStorageConnectionString") OutputBinding<String> outputItem) {
                for (String item : items) {
                    context.getLogger().info("Items has been changed: " + item);
                }
                outputItem.setValue(String.join(",", items));
                context.getLogger().info(items.length + " item(s) is/are changed.");
    }
}