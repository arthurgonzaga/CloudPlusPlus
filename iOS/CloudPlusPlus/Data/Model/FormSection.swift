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
    var title: String
    var fields: String
    
    init(id: String, structureId: String, index: Int, title: String, fields: String) {
        self.id = id
        self.structureId = structureId
        self.index = index
        self.title = title
        self.fields = fields
    }
}
