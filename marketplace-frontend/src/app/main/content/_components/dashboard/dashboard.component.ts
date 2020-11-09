import { Component, OnInit } from '@angular/core';
import { UserRole } from '../../_model/user';
import { Router } from '@angular/router';
import { HttpParameterCodec, HttpUrlEncodingCodec } from '@angular/common/http';

@Component({
  selector: "dashboard",
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss'],
})
export class DashboardComponent implements OnInit {
  public role: UserRole;

  constructor(private router: Router,
  ) { }

  sourceUrl: string;
  encodedUrl: string;
  decodedUrl: string;

  async ngOnInit() {
    const codec = new HttpUrlEncodingCodec();
    this.sourceUrl = 'http://localhost:4200/main/class-configurator?tenantId=5f92c841eada0c0d9dfa877a';

    this.encodedUrl = codec.encodeValue(this.sourceUrl);
    console.log(this.encodedUrl)
    this.decodedUrl = codec.decodeValue(this.encodedUrl);
    console.log(this.decodedUrl)

  }
}
