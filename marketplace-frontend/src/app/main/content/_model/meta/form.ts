import { ClassDefinition } from './class';
import { QuestionBase } from '../dynamic-forms/questions';
import { FormGroup } from '@angular/forms';
import { ClassProperty } from './property';
import { Relationship } from './relationship';
import { EnumDefinition } from './enum';

export class FormEntry {

    id: string;
    positionLevel: string;
    classDefinitions: ClassDefinition[];
    classProperties: ClassProperty<any>[];

    subEntries: FormEntry[];

    questions: QuestionBase<any>[] = [];
    formGroup: FormGroup;

    imagePath: string;
}

export class FormConfiguration {
    id: string;
    name: string;
    formEntry: FormEntry;

}

export class FormConfigurationPreviewRequest {
    classDefinitions: ClassDefinition[];
    relationships: Relationship[];
    rootClassDefinition: ClassDefinition;

    constructor(classDefinitions: ClassDefinition[], relationships: Relationship[], rootClassDefinition: ClassDefinition) {
        this.classDefinitions = classDefinitions;
        this.relationships = relationships;
        this.rootClassDefinition = rootClassDefinition;
    }
}

export class FormEntryReturnEventData {
    formGroup: FormGroup;
    formConfigurationId: string;

    constructor(formGroup: FormGroup, formConfigurationId: string) {
        this.formGroup = formGroup;
        this.formConfigurationId = formConfigurationId;
    }
}

// export class EnumRepresentation {
//     id: string;
//     enumEntries: EnumEntry[];
//     selectedEntries: EnumEntry[];
//     classDefinition: ClassDefinition;
// }

// export class EnumEntry {
//     level: number;
//     position: number[];
//     value: string;
//     selectable: boolean;
// }

