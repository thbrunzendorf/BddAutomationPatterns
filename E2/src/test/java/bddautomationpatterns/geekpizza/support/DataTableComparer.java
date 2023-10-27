package bddautomationpatterns.geekpizza.support;

import io.cucumber.datatable.DataTable;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DataTableComparer {

    public static <T> void assertMatchesToList(DataTable expectedDataTable, List<T> actualList){

        DataTable actualTable = createActualDataTable(expectedDataTable, actualList);
        expectedDataTable.diff(actualTable);
    }

    public static <T> void assertMatchesToListUnordered(DataTable expectedDataTable, List<T> actualList){

        DataTable actualTable = createActualDataTable(expectedDataTable, actualList);
        expectedDataTable.unorderedDiff(actualTable);
    }

    // creates a DataTable from the list of objects using the columns defined by the expectedDataTable.
    private static <T> DataTable createActualDataTable(DataTable expectedDataTable, List<T> actualList) {
        List<String> header = expectedDataTable.row(0);

        List<List<String>> actualTableRows = new ArrayList<>();
        actualTableRows.add(header);
        for (T actualItem: actualList) {
            List<String> row = header.stream().map(h -> getMatchingProperty(actualItem, h)).collect(Collectors.toList());
            actualTableRows.add(row);
        }

        return DataTable.create(actualTableRows);
    }

    private static <T> String getMatchingProperty(T actualItem, String header) {

        Object propertyValue = getProperty(actualItem, header);
        return propertyValue == null ? "" : propertyValue.toString();
    }

    // source: http://www.java2s.com/Code/Java/Reflection/Retrieveavaluefromaproperty.htm
    private static Object getProperty(Object obj, String property) {
        Object returnValue = null;

        try {
            String methodName = "get" + property.substring(0, 1).toUpperCase() + property.substring(1);
            Class clazz = obj.getClass();
            @SuppressWarnings("unchecked")
            Method method = clazz.getMethod(methodName);
            returnValue = method.invoke(obj);
        }
        catch (Exception e) {
            // Do nothing, we'll return the default value
        }

        return returnValue;
    }
}
