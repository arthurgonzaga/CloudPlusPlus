//
//  Field.swift
//  CloudPlusPlus
//
//  Created by arthur gonzaga on 18/05/25.
//

import Foundation

enum Field: Decodable {
    case textField(TextField)
    case description(Description)
    case number(Number)
    case dropdown(Dropdown)
    
    var id: String {
        switch self {
        case .textField(let field):
            field.id
        case .description(let field):
            field.id
        case .number(let field):
            field.id
        case .dropdown(let field):
            field.id
        }
    }
    
    var label: String {
        switch self {
        case .textField(let field):
            field.label
        case .description(let field):
            field.label
        case .number(let field):
            field.label
        case .dropdown(let field):
            field.label
        }
    }

    enum CodingKeys: String, CodingKey {
        case type
    }

    private enum FieldType: String, Codable {
        case textField = "text"
        case description = "description"
        case number = "number"
        case dropdown = "dropdown"
    }

    init(from decoder: Decoder) throws {
        let container = try decoder.container(keyedBy: CodingKeys.self)
        let type = try? container.decode(FieldType.self, forKey: .type)

        let singleValueContainer = try decoder.singleValueContainer()

        switch type {
        case .description:
            self = .description(try singleValueContainer.decode(Description.self))
        case .number:
            self = .number(try singleValueContainer.decode(Number.self))
        case .dropdown:
            self = .dropdown(try singleValueContainer.decode(Dropdown.self))
        default:
            self = .textField(try singleValueContainer.decode(TextField.self))
        }
    }
    
    struct TextField: Codable {
        let id: String
        let label: String
        let required: Bool
    }

    struct Description: Codable {
        let id: String
        let label: String
        let required: Bool
    }

    struct Number: Codable {
        let id: String
        let label: String
        let required: Bool
    }

    struct Dropdown: Codable {
        let id: String
        let label: String
        let required: Bool
        let options: [Option]

        struct Option: Codable, Identifiable {
            var id: String { value }
            let label: String
            let value: String
        }
    }
}

