import { html, LitElement } from 'lit';
import { customElement, state } from 'lit/decorators.js';
import '@vaadin/menu-bar';
import { MenuBarItem, MenuBarItemSelectedEvent } from '@vaadin/menu-bar';
import { applyTheme } from 'Frontend/generated/theme';

@customElement('menu-bar-basic')
export class Example extends LitElement {
  protected createRenderRoot() {
    const root = super.createRenderRoot();
    // Apply custom theme (only supported if your app uses one)
    applyTheme(root);
    return root;
  }

  @state()
  private items = [
    { text: 'View' },
    { text: 'Edit' },
    {
      text: 'Share',
      children: [
        {
          text: 'On social media',
          children: [{ text: 'Facebook' }, { text: 'Twitter' }, { text: 'Instagram' }],
        },
        { text: 'By email' },
        { text: 'Get link' },
      ],
    },
    {
      text: 'Move',
      children: [{ text: 'To folder' }, { text: 'To trash' }],
    },
    { text: 'Duplicate' },
  ];

  @state()
  private selectedItem?: MenuBarItem;

  render() {
    return html`
      <vaadin-menu-bar
        .items="${this.items}"
        @item-selected="${this.itemSelected}"
      ></vaadin-menu-bar>

      <div>Clicked item: ${this.selectedItem?.text}</div>
    `;
  }

  itemSelected(e: MenuBarItemSelectedEvent) {
    this.selectedItem = e.detail.value;
  }
}
