import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})
export class MenuComponent implements OnInit {

  userId!: string

  constructor(private activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.userId = this.activatedRoute.snapshot.params['userId']
  }
}
