/*
 * Copyright 2018 ThoughtWorks, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import {MithrilViewComponent} from "jsx/mithril-component";
import * as m from 'mithril';
import * as Buttons from "views/components/buttons/index";
import {CollapsiblePanel} from "views/components/collapsible_panel";
import {AlertFlashMessage, InfoFlashMessage, SuccessFlashMessage, WarnFlashMessage} from "views/components/flash_message";
import {ButtonGroup} from "views/components/icons";
import * as Icons from "views/components/icons/index";
import {KeyValuePair} from "views/components/key_value_pair";
import {Size} from "views/components/modal";
import {SampleModal} from "views/components/modal/sample";
import {SearchBox} from "views/components/search_box";

const HeaderPanel = require('views/components/header_panel');

export class KitchenSink extends MithrilViewComponent<null> {

  view(vnode: m.Vnode<null>) {
    return (
      <div>
        <HeaderPanel title="Kitchen Sink"/>
        <InfoFlashMessage message={"This page is awesome!"}/>
        <SuccessFlashMessage message={"Everything works as expected!"}/>
        <WarnFlashMessage message={"This might not work!"}/>
        <AlertFlashMessage message={"Disaster Happened!"}/>
        <AlertFlashMessage dismissible={true}
                           message={"Disaster Happened! But you can ignore by closing it"}/>
        <h3>CollapsiblePanel</h3>
        <CollapsiblePanel header={<div>Collapsible Panel header</div>}
                          actions={<button>foo</button>}>
          <div> Anything can go in the body</div>
        </CollapsiblePanel>
        <hr/>

        <h3>Search Box</h3>
        <SearchBox width={350} attrName="search" model={null} placeholder="Some placeholder"/>

        <h3>Modal</h3>
        <Buttons.Primary onclick={() => {this.createModal(Size.small); }}>Open Small Modal</Buttons.Primary>
        <Buttons.Primary onclick={() => {this.createModal(Size.medium); }}>Open Medium Modal</Buttons.Primary>
        <Buttons.Primary onclick={() => {this.createModal(Size.large); }}>Open Large Modal</Buttons.Primary>

        <h3>Icons</h3>
        <ButtonGroup>
          <Icons.Settings onclick={() => alert("You pressed settings button!")}/>
          <Icons.Analytics onclick={() => alert("You pressed analytics button!")}/>
          <Icons.Edit onclick={() => alert("You pressed edit button!")}/>
          <Icons.Clone onclick={() => alert("You pressed clone button!")}/>
          <Icons.Delete onclick={() => alert("You pressed delete button!")}/>
          <Icons.Lock onclick={() => alert("You pressed lock button!")}/>
          <Icons.Close onclick={() => alert("You pressed close button!")}/>
        </ButtonGroup>
        <ButtonGroup>
          <Icons.Settings disabled={true}/>
          <Icons.Analytics disabled={true}/>
          <Icons.Edit disabled={true}/>
          <Icons.Clone disabled={true}/>
          <Icons.Delete disabled={true}/>
          <Icons.Lock disabled={true}/>
          <Icons.Close disabled={true}/>

        </ButtonGroup>
        <hr/>

        <br/>

        <div>
          <h1>Work in progress components!</h1>
        </div>

        <h3>Buttons:</h3>
        <Buttons.Primary>Primary Button</Buttons.Primary>
        <Buttons.Secondary>Secondary Button</Buttons.Secondary>
        <Buttons.Reset>Reset Button</Buttons.Reset>
        <Buttons.Cancel>Cancel</Buttons.Cancel>
        <hr/>
        <h3>Disabled Buttons:</h3>
        <Buttons.Primary disabled={true}>Disabled Primary Button</Buttons.Primary>
        <Buttons.Secondary disabled={true}>Disabled Secondary Button</Buttons.Secondary>
        <Buttons.Reset disabled={true}>Disabled Reset Button</Buttons.Reset>
        <Buttons.Cancel disabled={true}>Disabled Cancel</Buttons.Cancel>

        <br/>
        <h3>Small Buttons:</h3>
        <Buttons.Primary small={true}>Small Primary Button</Buttons.Primary>
        <Buttons.Secondary small={true}>Small Secondary Button</Buttons.Secondary>
        <Buttons.Reset small={true}>Small Reset Button</Buttons.Reset>
        <Buttons.Cancel small={true}>Cancel</Buttons.Cancel>

        <h3>Some examples of accordions</h3>

        <h3>Some examples of key value pairs</h3>
        <KeyValuePair data={
          {
            'First Name':                                       'Jon',
            'Last Name':                                        'Doe',
            'email':                                            'jdoe@example.com',
            'some really really really really really long key': 'This is really really really really really really really really really really long junk value'
          }
        }/>

        <h3>Some examples of inline key value pairs</h3>
        <KeyValuePair inline={true} data={
          {
            'Plugin':                                           'my-fancy-plugin-name',
            'some really really really really really long key': 'This is really really really really really really really really really really long junk value'
          }
        }/>
      </div>
    );
  }

  private createModal(size: Size) {
    new SampleModal(size).render();
  }
}
