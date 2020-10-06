import { Component, OnInit } from "@angular/core";
import { User, UserRole } from "app/main/content/_model/user";
import { fuseAnimations } from "@fuse/animations";
import { FormGroup } from "@angular/forms";
import { GlobalInfo } from "app/main/content/_model/global-info";
import { UserService } from "app/main/content/_service/user.service";
import { LoginService } from "app/main/content/_service/login.service";
import { UserImage } from "app/main/content/_model/image";
import { CoreUserImageService } from "app/main/content/_service/core-user-image.service";
import { UserInfo } from "app/main/content/_model/userInfo";
import { CoreUserService } from "app/main/content/_service/core-user.service";

@Component({
  selector: "profile",
  templateUrl: "profile.component.html",
  styleUrls: ["profile.component.scss"],
  animations: fuseAnimations,
})
export class ProfileComponent implements OnInit {
  user: User;
  globalInfo: GlobalInfo;
  userInfo: UserInfo;
  userImage: UserImage;
  currentRoles: UserRole[] = [];

  loaded: boolean;

  constructor(
    private loginService: LoginService,
    private userImageService: CoreUserImageService,
    private userService: CoreUserService
  ) {}

  async ngOnInit() {
    this.loaded = false;
    this.globalInfo = this.loginService.getGlobalInfo();
    this.userInfo = this.globalInfo.userInfo;
    this.currentRoles = this.globalInfo.userSubscriptions.map((s) => s.role);

    this.user = <User>(
      await this.userService.findById(this.userInfo.id).toPromise()
    );

    // Don't wait for image...
    this.userImageService
      .findByUserId(this.userInfo.id)
      .toPromise()
      .then((userImage: UserImage) => (this.userImage = userImage));

    this.loaded = true;
  }

  getProfileImage() {
    return this.userImageService.getUserProfileImage(this.userImage);
  }

  hasVolunteerRole() {
    return this.currentRoles.indexOf(UserRole.VOLUNTEER) !== -1;
  }
}
