//
//  Mapper.swift
//  CloudPlusPlus
//
//  Created by arthur gonzaga on 18/05/25.
//

import Foundation

struct Mapper {
    
    static func getFormStructureList(structureStringList: [String]) -> [FormStructure] {
        structureStringList.compactMap { structureJson in
            let json = try! JSONSerialization.jsonObject(with: structureJson.data(using: .utf8)!) as! [String: Any]
            let title = json["title"] as! String
            let fields = json["fields"] as! [Any]

            return FormStructure(
                id: UUID().uuidString,
                name: title,
                totalFields: fields.count
            )
        }
    }

    static func getFormSectionList(json: String, structureId: String) -> [FormSection] {
        guard
            let data = json.data(using: .utf8),
            let jsonObj = try? JSONSerialization.jsonObject(with: data) as? [String: Any],
            let sectionsArray = jsonObj["sections"],
            let sectionsData = try? JSONSerialization.data(withJSONObject: sectionsArray),
            let sections = try? JSONDecoder().decode([Section].self, from: sectionsData),
            let fieldsJsonArray = jsonObj["fields"] as? [Any]
        else {
            return []
        }

        return sections.enumerated().compactMap { (index, section) in
            let fieldSubArray = Array(fieldsJsonArray[section.from..<section.to])
            let fieldData = try! JSONSerialization.data(withJSONObject: fieldSubArray)
            let fieldString = String(data: fieldData, encoding: .utf8)!

            return FormSection(
                id: section.id,
                structureId: structureId,
                index: index,
                title: section.title,
                fields: fieldString
            )
        }
    }
    
    private struct Section: Codable {
        let id: String
        let title: String
        let from: Int
        let to: Int
        let index: Int

        enum CodingKeys: String, CodingKey {
            case id = "uuid"
            case title
            case from
            case to
            case index
        }
    }
}
