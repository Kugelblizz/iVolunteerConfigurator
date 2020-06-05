import { Component, OnInit, Input, ElementRef, ViewChild } from '@angular/core';
import { FormGroup, Validators, FormBuilder } from '@angular/forms';
import { mxgraph } from 'mxgraph';
import { Router } from '@angular/router';
import { ObjectIdService } from 'app/main/content/_service/objectid.service.';
import { Marketplace } from 'app/main/content/_model/marketplace';
import { Helpseeker } from 'app/main/content/_model/helpseeker';
import { MyMxCell } from '../../../myMxCell';


declare var require: any;

const mx: typeof mxgraph = require('mxgraph')({
    // mxDefaultLanguage: 'de',
    // mxBasePath: './mxgraph_resources',
});

// tslint:disable-next-line: class-name


@Component({
    selector: 'app-enum-graph-editor',
    templateUrl: './enum-graph-editor.component.html',
    styleUrls: ['./enum-graph-editor.component.scss'],
    // providers: [DialogFactoryDirective]
})
export class EnumGraphEditorComponent implements OnInit {
    constructor(
        private router: Router,
        private objectIdService: ObjectIdService,
        // private dialogFactory: DialogFactoryDirective,
    ) { }

    @Input() marketplace: Marketplace;
    @Input() helpseeker: Helpseeker;



    @ViewChild('enumGraphContainer', { static: true }) graphContainer: ElementRef;

    graph: mxgraph.mxGraph;
    /**
     * ******INITIALIZATION******
     */

    ngOnInit() {
    }

    ngAfterContentInit() {
        // this.graphContainer.nativeElement.style.position = 'absolute';
        this.graphContainer.nativeElement.style.overflow = 'hidden';
        // this.graphContainer.nativeElement.style.left = '0px';
        // this.graphContainer.nativeElement.style.top = '35px';
        // this.graphContainer.nativeElement.style.right = '0px';
        // this.graphContainer.nativeElement.style.bottom = '0px';
        this.graphContainer.nativeElement.style.height = '50vh';
        this.graphContainer.nativeElement.style.width = '100%';
        this.graphContainer.nativeElement.style.background = 'white';

        this.graph = new mx.mxGraph(this.graphContainer.nativeElement);
        this.graph.isCellSelectable = function (cell) {
            const state = this.view.getState(cell);
            const style = (state != null) ? state.style : this.getCellStyle(cell);

            return this.isCellsSelectable() && !this.isCellLocked(cell) && style['selectable'] !== 0;
        };

        const outer = this;
        this.graph.getCursorForCell = function (cell: MyMxCell) {
            // todo cursor
        };

        const modelGetStyle = this.graph.model.getStyle;

        this.graph.model.getStyle = function (cell) {
            if (cell != null) {
                let style = modelGetStyle.apply(this, arguments);

                if (this.isCollapsed(cell)) {
                    style = style + ';shape=rectangle';
                }
                return style;
            }
            return null;
        };

        if (!mx.mxClient.isBrowserSupported()) {
            mx.mxUtils.error('Browser is not supported!', 200, false);
        } else {
            // Disables the built-in context menu
            mx.mxEvent.disableContextMenu(this.graphContainer.nativeElement);

            // Enables rubberband selection
            // tslint:disable-next-line: no-unused-expression
            new mx.mxRubberband(this.graph);

            this.graph.setPanning(true);

            // this.graph.popupMenuHandler = this.createPopupMenu(this.graph);
            this.graph.tooltipHandler = new mx.mxTooltipHandler(this.graph, 100);

            /**
             * ******EVENT LISTENERS******
             */

            this.graph.addListener(mx.mxEvent.CLICK, function (sender: mxgraph.mxGraph, evt: mxgraph.mxEventObject) {
                // Todo click event
                console.log(evt);
                outer.graph.getModel().beginUpdate();
                // outer.graph.insertVertex(outer.graph.getDefaultParent(), null, null, )
                outer.graph.getModel().endUpdate();
            });


            this.graph.addListener(mx.mxEvent.DOUBLE_CLICK, function (sender: mxgraph.mxGraph, evt: mxgraph.mxEventObject) {
                // TODO double click event
            });

        }
    }


    navigateBack() {
        window.history.back();
    }


}
