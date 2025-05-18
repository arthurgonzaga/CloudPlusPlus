//
//  FormFieldValue.swift
//  CloudPlusPlus
//
//  Created by arthur gonzaga on 18/05/25.
//
import SwiftData

@Model
class FormFieldValue {
    var id: String
    var formId: String
    var sectionId: String
    var value: String
    
    init(id: String, formId: String, sectionId: String, value: String) {
        self.id = id
        self.formId = formId
        self.sectionId = sectionId
        self.value = value
    }
}
