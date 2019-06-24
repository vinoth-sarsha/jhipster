import { browser, ExpectedConditions, element, by, ElementFinder } from 'protractor';

export class Pp_userComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-pp-user div table .btn-danger'));
  title = element.all(by.css('jhi-pp-user div h2#page-heading span')).first();

  async clickOnCreateButton(timeout?: number) {
    await this.createButton.click();
  }

  async clickOnLastDeleteButton(timeout?: number) {
    await this.deleteButtons.last().click();
  }

  async countDeleteButtons() {
    return this.deleteButtons.count();
  }

  async getTitle() {
    return this.title.getText();
  }
}

export class Pp_userUpdatePage {
  pageTitle = element(by.id('jhi-pp-user-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  user_nameInput = element(by.id('field_user_name'));
  dobInput = element(by.id('field_dob'));
  roleInput = element(by.id('field_role'));
  locationInput = element(by.id('field_location'));
  about_meInput = element(by.id('field_about_me'));

  async getPageTitle() {
    return this.pageTitle.getText();
  }

  async setUser_nameInput(user_name) {
    await this.user_nameInput.sendKeys(user_name);
  }

  async getUser_nameInput() {
    return await this.user_nameInput.getAttribute('value');
  }

  async setDobInput(dob) {
    await this.dobInput.sendKeys(dob);
  }

  async getDobInput() {
    return await this.dobInput.getAttribute('value');
  }

  async setRoleInput(role) {
    await this.roleInput.sendKeys(role);
  }

  async getRoleInput() {
    return await this.roleInput.getAttribute('value');
  }

  async setLocationInput(location) {
    await this.locationInput.sendKeys(location);
  }

  async getLocationInput() {
    return await this.locationInput.getAttribute('value');
  }

  async setAbout_meInput(about_me) {
    await this.about_meInput.sendKeys(about_me);
  }

  async getAbout_meInput() {
    return await this.about_meInput.getAttribute('value');
  }

  async save(timeout?: number) {
    await this.saveButton.click();
  }

  async cancel(timeout?: number) {
    await this.cancelButton.click();
  }

  getSaveButton(): ElementFinder {
    return this.saveButton;
  }
}

export class Pp_userDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-pp_user-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-pp_user'));

  async getDialogTitle() {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
