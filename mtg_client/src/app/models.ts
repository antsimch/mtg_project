export interface Deck {
    deckId: string,
    deckName: string,
    userId: string,
    draftId: string,
    cards: string[]
}

export interface Draft {
    draftId: string,
    draftSet: string,
    draftDate: string,
    numberOfPlayers: number
}

export interface Card {
    cardId: string,
    cardName: string,
    cardCMC: number,
    cardColors: string[]
    cardType: string,
    cardRarity: string,
    cardSet: string,
    cardImageUrl: string
}