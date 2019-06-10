import { Component, OnInit } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { MatTableDataSource } from '@angular/material';
import { fuseAnimations } from '@fuse/animations';

import { PropertyListItem, Property, PropertyKind } from "../_model/properties/Property";

import { LoginService } from '../_service/login.service';
import { CoreHelpSeekerService } from '../_service/core-helpseeker.service';
import { Participant } from '../_model/participant';
import { Marketplace } from '../_model/marketplace';

import { PropertyService } from "../_service/property.service";
import { isNullOrUndefined } from 'util';

@Component({
  selector: 'app-property-list',
  templateUrl: './property-list.component.html',
  styleUrls: ['./property-list.component.scss'],
  animations: fuseAnimations
})
export class PropertyListComponent implements OnInit {

  dataSource = new MatTableDataSource<PropertyListItem>();
  displayedColumns = ['id', 'name', 'defaultValue', 'kind', 'actions'];
  // displayedColumns = ['id', 'name', 'defaultValue', 'kind'];

  marketplace: Marketplace;

  propertyArray: PropertyListItem[];

  customOnly: boolean;
  isLoaded: boolean;

  constructor(private router: Router,
    private propertyService: PropertyService,
    private loginService: LoginService,
    private helpSeekerService: CoreHelpSeekerService) {
    }

  ngOnInit() {
    this.isLoaded = false;
    this.customOnly = false;
    this.loadAllProperties();

  }

  onRowSelect(p: PropertyListItem) {
    console.log("Property Clicked: " + p.name );
    console.log("CURRENT URL: " + this.router.url)
    this.router.navigate(['/main/properties/' + this.marketplace.id + '/' + p.id]);
  }


 
  loadAllProperties() {
    console.log ("load props from Server...");

   
    this.loginService.getLoggedIn().toPromise().then((helpSeeker: Participant) => {
      this.helpSeekerService.findRegisteredMarketplaces(helpSeeker.id).toPromise().then((marketplace: Marketplace) => {
        if (!isNullOrUndefined(marketplace)) {
          this.marketplace = marketplace;
          this.propertyService.getPropertyList(marketplace).toPromise().then((pArr: PropertyListItem[]) => {
            this.propertyArray = pArr;
            this.updateDataSource();
            console.log(pArr);
            this.isLoaded = true;
        })}
      })
    });
  }

  updateDataSource() {
    let ret: PropertyListItem[] = [];

    for (let property of this.propertyArray) {
      if (!this.customOnly) {
        ret.push(property);
      }  else {
        property.custom ? ret.push(property) : null ;
      }
    }

    this.dataSource.data = ret;
  }

  

  viewPropertyAction(property: PropertyListItem) {
    console.log("clicked view Property")
    this.router.navigate(['main/property/detail/view/' + this.marketplace.id + '/' + property.id],{queryParams: {ref: 'list'}});
    console.log(property);
  }

  newPropertyAction() {
    console.log("clicked new Property");
    this.router.navigate(['main/property/detail/edit/' + this.marketplace.id + '/'] );
  }

  editPropertyAction(property: PropertyListItem) { 
    console.log("clicked edit Property: ");
    this.router.navigate(['main/property/detail/edit/' + this.marketplace.id + '/' + property.id]);

    console.log(property)

    console.log("TODO create detail/edit page");

  }

  deletePropertyAction(property: PropertyListItem) {
    console.log("clicked delete Property: ");
    console.log(property)

    this.propertyService.deleteProperty(this.marketplace, property.id).toPromise().then(() => {
      this.ngOnInit();
      console.log("done");
    });


  }


  // updateProperty(item: PropertyListItem) {
  // console.log("clicked to update property " + item.id + " " + item.name + " TODO - link to detail page");

  // }

  // updatePropertySave(property: Property<string>) {
  //   console.log("attempt to update");
  //   this.loginService.getLoggedIn().toPromise().then((helpSeeker: Participant) => {
  //     this.helpSeekerService.findRegisteredMarketplaces(helpSeeker.id).toPromise().then((marketplace: Marketplace) => {
  //       if (!isNullOrUndefined(marketplace)) {
  //         this.propertyService.updateProperty(marketplace, <Property<string>>property).toPromise().then(() => 
  //           console.log("Updated"));
          
  //       }
  //     })
  //   });
  //}

  // displayPropertyValue(property: PropertyListItem): string {    
  //   return PropertyListItem.getValue(property);
  // }
}