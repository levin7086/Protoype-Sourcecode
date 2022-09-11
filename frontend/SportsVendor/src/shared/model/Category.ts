export interface Category {
  displayName: string;
  name: string;
  // children: Category[],
  // parent: Category;
}

export const BEACH_VOLLEYBALL = {
  displayName: 'Beach Volleyball',
  name: 'BEACH_VOLLEYBALL'
}

export const INDOOR_VOLLEYBALL = {
  displayName: 'Indoor Volleyball',
  name: 'INDOOR_VOLLEYBALL'
}

export const TENNIS = {
  displayName: 'Tennis',
  name: 'TENNIS'
}

export const VOLLEYBALL = {
  displayName: 'Volleyball',
  name: 'VOLLEYBALL',
  children: [BEACH_VOLLEYBALL, INDOOR_VOLLEYBALL]
}

export const FOOTBALL = {
  displayName: 'Football',
  name: 'FOOTBALL'
}

export const CATEGORIES: Category[] = [
  VOLLEYBALL,
  BEACH_VOLLEYBALL,
  INDOOR_VOLLEYBALL,
  TENNIS,
  FOOTBALL
]
