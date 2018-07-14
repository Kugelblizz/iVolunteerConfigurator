import {Component, OnDestroy, OnInit} from '@angular/core';
import {Competence} from '../_model/competence';
import {CompetenceService} from '../_service/competence.service';
import {fuseAnimations} from '@fuse/animations';
import {ActivatedRoute} from '@angular/router';
import {LoginService} from '../_service/login.service';
import {Participant} from '../_model/participant';
import {VolunteerProfileService} from '../_service/volunteer-profile.service';
import {CoreVolunteerService} from '../_service/core-volunteer.service';
import {Marketplace} from '../_model/marketplace';
import {Subscription} from 'rxjs';
import {MessageService} from '../_service/message.service';

@Component({
  selector: 'fuse-competencies',
  templateUrl: './competencies.component.html',
  styleUrls: ['./competencies.component.scss'],
  animations: fuseAnimations
})
export class FuseCompetenceListComponent implements OnInit, OnDestroy {


  private competencies: Competence[] = [];
  private pageType: any;
  private marketplaceChangeSubscription: Subscription;
  private header: string;

  constructor(private competenceService: CompetenceService,
              private volunteerProfileService: VolunteerProfileService,
              private coreVolunteerService: CoreVolunteerService,
              private loginService: LoginService,
              private messageService: MessageService,
              private route: ActivatedRoute) {
  }

  ngOnInit() {

    this.loadCompetencies();
    this.marketplaceChangeSubscription = this.messageService.subscribe('marketplaceSelectionChanged', this.loadCompetencies.bind(this));
  }

  private loadCompetencies() {
    this.route.paramMap.subscribe(
      params => {
        this.pageType = params.get('pageType');
        switch (this.pageType) {
          case 'all':
            this.header = 'All Competencies';
            this.competencies = [];
            this.loginService.getLoggedIn().toPromise().then((volunteer: Participant) => {
              const selected_marketplaces = JSON.parse(localStorage.getItem('marketplaces'));
              this.coreVolunteerService.findRegisteredMarketplaces(volunteer.id).toPromise().then((marketplaces: Marketplace[]) => {
                marketplaces
                  .filter(mp => selected_marketplaces.find(selected_mp => selected_mp.id === mp.id))
                  .forEach(marketplace => {
                    this.competenceService.findAll(marketplace).toPromise().then((comp: Competence[]) => {
                      this.competencies = this.competencies.concat(comp);
                    });
                  });
              });
            });
            break;
          case 'my':
            this.header = 'My Competencies';
            this.competencies = [];
            this.loginService.getLoggedIn().toPromise().then((volunteer: Participant) => {
              const selected_marketplaces = JSON.parse(localStorage.getItem('marketplaces'));
              this.coreVolunteerService.findRegisteredMarketplaces(volunteer.id).toPromise().then((marketplaces: Marketplace[]) => {
                marketplaces
                  .filter(mp => selected_marketplaces.find(selected_mp => selected_mp.id === mp.id))
                  .forEach(marketplace => {
                    this.volunteerProfileService.findCompetencesByVolunteer(marketplace, volunteer).toPromise().then((comp: Competence[]) => {
                      this.competencies = this.competencies.concat(comp);
                    });
                  });
              });
            });
            break;
        }
      }
    );
  }


  ngOnDestroy() {
    this.marketplaceChangeSubscription.unsubscribe();
  }

}
