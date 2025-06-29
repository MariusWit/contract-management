import { LitElement, html, css, customElement } from 'lit-element';
import '@vaadin/icon/src/vaadin-icon.js';
import '@vaadin/vaadin-ordered-layout/src/vaadin-vertical-layout.js';
import '@vaadin/horizontal-layout/src/vaadin-horizontal-layout.js';
import '@vaadin/button/src/vaadin-button.js';
import '@vaadin/vertical-layout/src/vaadin-vertical-layout.js';
import '@vaadin/app-layout/src/vaadin-drawer-toggle.js';
import '@vaadin/text-field/src/vaadin-text-field.js';
import '@vaadin/grid/src/vaadin-grid.js';
import './contact-form';

@customElement('main-view')
export class MainView extends LitElement {
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
<vaadin-vertical-layout style="width: 100%; height: 100%; padding: var(--lumo-space-m);">
 <vaadin-horizontal-layout theme="spacing">
  <vaadin-text-field id="filterText" type="text" placeholder="Filter by name…" clear-button-visible></vaadin-text-field>
  <vaadin-button id="addContactButton" tabindex="0">
   Add contact
  </vaadin-button>
 </vaadin-horizontal-layout>
 <vaadin-horizontal-layout theme="spacing" style="width: 100%; height: 100%;">
  <vaadin-grid id="grid" style="width: 100%; height: 100%; flex: 2" is-attached></vaadin-grid>
  <contact-form id="contactForm" style="flex: 1"></contact-form>
 </vaadin-horizontal-layout>
</vaadin-vertical-layout>
`;
  }

  // Remove this method to render the contents of this view inside Shadow DOM
  createRenderRoot() {
    return this;
  }
}
