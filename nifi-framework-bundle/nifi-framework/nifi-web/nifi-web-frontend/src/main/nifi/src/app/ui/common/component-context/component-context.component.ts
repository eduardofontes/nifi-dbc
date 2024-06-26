/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import { Component, Input } from '@angular/core';
import { ComponentType } from '../../../state/shared';
import { ComponentTypeNamePipe } from '../../../pipes/component-type-name.pipe';

@Component({
    selector: 'component-context',
    standalone: true,
    imports: [ComponentTypeNamePipe],
    templateUrl: './component-context.component.html',
    styleUrl: './component-context.component.scss'
})
export class ComponentContext {
    private _componentType: ComponentType = ComponentType.Processor;
    componentIconClass: string = '';

    @Input() set type(type: ComponentType) {
        this._componentType = type;
        this.componentIconClass = this.getIconClassName(type);
    }

    get type(): ComponentType {
        return this._componentType;
    }

    @Input() id: string | null = null;
    @Input() name: string = '';

    private getIconClassName(type: ComponentType) {
        switch (type) {
            case ComponentType.Connection:
                return 'icon-connect';
            case ComponentType.Processor:
                return 'icon-processor';
            case ComponentType.OutputPort:
                return 'icon-port-out';
            case ComponentType.InputPort:
                return 'icon-port-in';
            case ComponentType.ProcessGroup:
                return 'icon-group';
            case ComponentType.Funnel:
                return 'icon-funnel';
            case ComponentType.Label:
                return 'icon-label';
            case ComponentType.RemoteProcessGroup:
                return 'icon-group-remote';
            default:
                return 'icon-connect';
        }
    }
}
