import { ViewChild, ElementRef, Component, ChangeDetectorRef, Input, EventEmitter, Output, OnInit, OnChanges } from '@angular/core';
import { EnumOptionsOverlayContentData } from '../options-overlay-content/options-overlay-content.component';

const OVERLAY_WIDTH = 100;
const OVERLAY_HEIGHT = 100;


@Component({
  selector: 'enum-options-overlay-control',
  templateUrl: './options-overlay-control.component.html',
  styleUrls: ['./options-overlay-control.component.scss']
})
export class EnumOptionsOverlayControlComponent implements OnInit, OnChanges {


  @ViewChild('overlayDiv', { static: false }) overlayDiv: ElementRef;
  @Input() displayOverlay: boolean;
  @Input() overlayContent: any;
  @Input() overlayEvent: PointerEvent;
  @Output() overlayClosed = new EventEmitter<any>();


  constructor(
    private changeDetector: ChangeDetectorRef
  ) { }

  ngOnInit() {
    console.log("overlay");
    console.log(this.overlayEvent);
    console.log(this.displayOverlay);
    this.toggleOverlay();
  }

  ngOnChanges() {
    if (this.displayOverlay) {
      this.ngOnInit();
    }
  }

  navigateBack() {
    window.history.back();
  }

  toggleOverlay() {
    this.changeDetector.detectChanges();

    if (this.displayOverlay) {
      let yPos = this.overlayEvent.clientY;
      let xPos = this.overlayEvent.clientX;

      if (yPos + OVERLAY_HEIGHT > window.innerHeight) {
        yPos = window.innerHeight - OVERLAY_HEIGHT;
      }

      if (xPos + OVERLAY_WIDTH > window.innerWidth) {
        xPos = window.innerWidth - OVERLAY_WIDTH;
      }

      this.overlayDiv.nativeElement.style.top = yPos + 'px';
      this.overlayDiv.nativeElement.style.left = xPos + 'px';
      this.overlayDiv.nativeElement.style.position = 'fixed';
      this.overlayDiv.nativeElement.style.width = OVERLAY_WIDTH + 'px';
      this.overlayDiv.nativeElement.style.height = OVERLAY_HEIGHT + 'px';
    }
  }

  handleResultEvent(event: EnumOptionsOverlayContentData) {
    this.displayOverlay = false;
    this.overlayClosed.emit(event);
  }

  // closeOverlay(event: ClassOptionsOverlayContentData) {
  //   this.displayOverlay = false;
  //   this.overlayClosed.emit(undefined);
  // }
}