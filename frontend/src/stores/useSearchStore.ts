import { userCar } from '@/interfaces/userCar';
import { create } from 'zustand';

type SearchState = {
  location: string;
  startDate: Date | null;
  endDate: Date | null;
  cars: userCar[] | null;
  setLocation: (location: string) => void;
  setStartDate: (date: Date | null) => void;
  setEndDate: (date: Date | null) => void;
  setCars: (cars: userCar[]) => void;
};

export const useSearchStore = create<SearchState>()((set) => ({
  location: '',
  startDate: null,
  endDate: null,
  cars: null,
  setLocation: (location) => set({ location }),
  setStartDate: (date) => set({ startDate: date }),
  setEndDate: (date) => set({ endDate: date }),
  setCars: (cars) => set({ cars }),
}));
