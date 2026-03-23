import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { PlayerService } from '../../services/player.service';
import { Player } from '../../models/player.model';

@Component({
  selector: 'app-players',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './players.component.html'
})
export class PlayersComponent implements OnInit {
  players: Player[] = [];
  loading = false;
  showForm = false;
  editingId: number | null = null;

  form: Player = { name: '', monthlyFee: 0 };

  constructor(private playerService: PlayerService) {}

  ngOnInit(): void {
    this.loadPlayers();
  }

  loadPlayers(): void {
    this.loading = true;
    this.playerService.findAll().subscribe({
      next: data => { this.players = data; this.loading = false; },
      error: () => this.loading = false
    });
  }

  openForm(player?: Player): void {
    if (player) {
      this.form = { name: player.name, monthlyFee: player.monthlyFee };
      this.editingId = player.id!;
    } else {
      this.form = { name: '', monthlyFee: 0 };
      this.editingId = null;
    }
    this.showForm = true;
  }

  cancelForm(): void {
    this.showForm = false;
    this.editingId = null;
    this.form = { name: '', monthlyFee: 0 };
  }

  save(): void {
    if (!this.form.name || this.form.monthlyFee <= 0) return;
    const action = this.editingId
      ? this.playerService.update(this.editingId, this.form)
      : this.playerService.create(this.form);

    action.subscribe({
      next: () => { this.loadPlayers(); this.cancelForm(); },
      error: err => console.error(err)
    });
  }

  toggleActive(player: Player): void {
    this.playerService.toggleActive(player.id!).subscribe({
      next: updated => {
        player.active = updated.active;
      }
    });
  }

  delete(id: number): void {
    if (!confirm('Excluir permanentemente este jogador?')) return;
    this.playerService.delete(id).subscribe(() => this.loadPlayers());
  }
}
