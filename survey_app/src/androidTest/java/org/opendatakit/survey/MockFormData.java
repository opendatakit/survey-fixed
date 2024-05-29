package org.opendatakit.survey;

import org.opendatakit.survey.utilities.FormInfo;

import java.util.ArrayList;
import java.util.List;

public class MockFormData {

    public static ArrayList<FormInfo> generateMockForms() {
        ArrayList<FormInfo> mockForms = new ArrayList<>();

        mockForms.add(new FormInfo("tableId1", "formId1", "version1", "Form 1", "Subtext 1"));
        mockForms.add(new FormInfo("tableId2", "formId2", "version2", "Form 2", "Subtext 2"));
        mockForms.add(new FormInfo("tableId3", "formId3", "version3", "Form 3", "Subtext 3"));
        mockForms.add(new FormInfo("tableId4", "formId4", "version4", "Form 4", "Subtext 4"));
        mockForms.add(new FormInfo("tableId5", "formId5", "version5", "Form 5", "Subtext 5"));

        // Logging for debugging
        for (FormInfo form : mockForms) {
            System.out.println("MockForm: " + form.formDisplayName);
        }

        return mockForms;
    }

}
