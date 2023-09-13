export interface Deck {
    deckId: string
    deckName: string
    userId: string
    draftId: string
    cards: string[]
}

export interface DraftDetails {
    draftId: string
    draftSet: string
    draftDate: string
    numberOfPlayers: number
    decksCreated: number
}

export interface Card {
    cardId: string
    cardName: string
    cardCMC: number
    cardType: string
    cardRarity: string
    cardSet: string
    cardImageUrl: string
}

export interface Player {
    playerName: string
    playerId: string
}

export interface CardPick {
    draftId: string
    index: number
}

export interface Draft {
    draftId: string
    player: Player
    status: string
    set: string
    cards: Card[]
}