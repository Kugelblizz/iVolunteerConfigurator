import { Component, OnInit } from '@angular/core';
import { UserRole } from '../../_model/user';
import { Router } from '@angular/router';

@Component({
  selector: "dashboard",
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss'],
})
export class DashboardComponent implements OnInit {
  public role: UserRole;

  constructor(private router: Router) { }

  async ngOnInit() {

  }
}
