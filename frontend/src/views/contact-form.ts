import { LitElement, html, css, customElement } from 'lit-element';
import '@vaadin/form-layout/src/vaadin-form-layout.js';
import '@vaadin/text-field/src/vaadin-text-field.js';
import '@vaadin/email-field/src/vaadin-email-field.js';
import '@vaadin/horizontal-layout/src/vaadin-horizontal-layout.js';
import '@vaadin/button/src/vaadin-button.js';
import '@vaadin/combo-box/src/vaadin-combo-box.js';

@customElement('contact-form')
export class ContactForm extends LitElement {
  static get styles() {
    return css`
      :host {
          display: block;
          height: 100%;
      }
      `;
  }

  render() {
    return html`
<vaadin-form-layout style="width: 100%; height: 100%;">
 <vaadin-text-field type="text" label="First name" id="firstName"></vaadin-text-field>
 <vaadin-text-field type="text" label="Last name" id="lastName"></vaadin-text-field>
 <vaadin-email-field label="Email" type="email" id="email"></vaadin-email-field>
 <vaadin-combo-box id="company" label="Company"></vaadin-combo-box>
 <vaadin-combo-box id="status" label="Status"></vaadin-combo-box>
 <vaadin-horizontal-layout theme="spacing">
  <vaadin-button theme="primary" tabindex="0" id="save">
    Save 
  </vaadin-button>
  <vaadin-button theme="primary error" tabindex="0" id="delete">
    Delete 
  </vaadin-button>
  <vaadin-button theme="tertiary" tabindex="0" id="close">
    Close 
  </vaadin-button>
 </vaadin-horizontal-layout>
</vaadin-form-layout>
`;
  }

  // Remove this method to render the contents of this view inside Shadow DOM
  createRenderRoot() {
    return this;
  }
}
