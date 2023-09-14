export interface User {
    userId: string
    username: string
    userEmail: string
    userPassword: string
}

export interface LoginDetails {
    username: string
    password: string
}

export interface Deck {
    deckId: string
    deckName: string
    userId: string
    cards: string[]
}

export interface DraftDetails {
    draftId: string
    draftSet: string
    userId: string
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