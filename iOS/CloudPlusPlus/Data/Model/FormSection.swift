//
//  FormSections.swift
//  CloudPlusPlus
//
//  Created by arthur gonzaga on 18/05/25.
//
import SwiftData

@Model
class FormSection {
    var id: String
    var structureId: String
    var index: Int
    var fields: String
    
    init(id: String, structureId: String, index: Int, fields: String) {
        self.id = id
        self.structureId = structureId
        self.index = index
        self.fields = fields
    }
}
