/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../../page-objects/jhi-page-objects';

import { Pp_userComponentsPage, Pp_userDeleteDialog, Pp_userUpdatePage } from './pp-user.page-object';

const expect = chai.expect;

describe('Pp_user e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let pp_userUpdatePage: Pp_userUpdatePage;
  let pp_userComponentsPage: Pp_userComponentsPage;
  let pp_userDeleteDialog: Pp_userDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Pp_users', async () => {
    await navBarPage.goToEntity('pp-user');
    pp_userComponentsPage = new Pp_userComponentsPage();
    await browser.wait(ec.visibilityOf(pp_userComponentsPage.title), 5000);
    expect(await pp_userComponentsPage.getTitle()).to.eq('Pp Users');
  });

  it('should load create Pp_user page', async () => {
    await pp_userComponentsPage.clickOnCreateButton();
    pp_userUpdatePage = new Pp_userUpdatePage();
    expect(await pp_userUpdatePage.getPageTitle()).to.eq('Create or edit a Pp User');
    await pp_userUpdatePage.cancel();
  });

  it('should create and save Pp_users', async () => {
    const nbButtonsBeforeCreate = await pp_userComponentsPage.countDeleteButtons();

    await pp_userComponentsPage.clickOnCreateButton();
    await promise.all([
      pp_userUpdatePage.setUser_nameInput('user_name'),
      pp_userUpdatePage.setDobInput('2000-12-31'),
      pp_userUpdatePage.setRoleInput('role'),
      pp_userUpdatePage.setLocationInput('location'),
      pp_userUpdatePage.setAbout_meInput('about_me')
    ]);
    expect(await pp_userUpdatePage.getUser_nameInput()).to.eq('user_name', 'Expected User_name value to be equals to user_name');
    expect(await pp_userUpdatePage.getDobInput()).to.eq('2000-12-31', 'Expected dob value to be equals to 2000-12-31');
    expect(await pp_userUpdatePage.getRoleInput()).to.eq('role', 'Expected Role value to be equals to role');
    expect(await pp_userUpdatePage.getLocationInput()).to.eq('location', 'Expected Location value to be equals to location');
    expect(await pp_userUpdatePage.getAbout_meInput()).to.eq('about_me', 'Expected About_me value to be equals to about_me');
    await pp_userUpdatePage.save();
    expect(await pp_userUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await pp_userComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Pp_user', async () => {
    const nbButtonsBeforeDelete = await pp_userComponentsPage.countDeleteButtons();
    await pp_userComponentsPage.clickOnLastDeleteButton();

    pp_userDeleteDialog = new Pp_userDeleteDialog();
    expect(await pp_userDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Pp User?');
    await pp_userDeleteDialog.clickOnConfirmButton();

    expect(await pp_userComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
