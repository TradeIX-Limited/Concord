import { CordaxPage } from './app.po';

describe('cordax App', () => {
  let page: CordaxPage;

  beforeEach(() => {
    page = new CordaxPage();
  });

  it('should display message saying app works', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('app works!');
  });
});
