import { Component, ViewChild } from '@angular/core';
import { Platform, MenuController, Nav } from 'ionic-angular';
import { StatusBar } from '@ionic-native/status-bar';
import { SplashScreen } from '@ionic-native/splash-screen';

import { LastResultsPage } from '../pages/lastResults/lastResults';
import { HomePage } from '../pages/home/home';

@Component({
  templateUrl: 'app.html'
})
export class MyApp {
  @ViewChild(Nav) nav: Nav;
  
  rootPage:any = HomePage;
  items: Array<{item: string, page: any}>;

  constructor(platform: Platform, statusBar: StatusBar,public menu: MenuController, splashScreen: SplashScreen) {
    platform.ready().then(() => {
      // Okay, so the platform is ready and our plugins are available.
      // Here you can do any higher level native things you might need.
      statusBar.styleDefault();
      splashScreen.hide();
    });
	
	// set our app's pages
    this.items = [
	{item: 'Go To Calculator', page: HomePage},
	{item: 'Last Calculator Results', page: LastResultsPage}
    ];
  }
  
  openPage(nextPage) {
    // close the menu when clicking a link from the menu
    this.menu.close();
    // navigate to the new page if it is not the current page
    this.nav.setRoot(nextPage.page);
  }
}

