import { Header } from "../header/Header"
import { CarCard } from "../carCard/CarCard"
import { CarMainCategoriesPanel } from "../CarMainCategoriesPanel/CarMainCategoriesPanel"
import { useSearchStore } from "@/stores/useSearchStore"

export const SearchPage: React.FC = () => {
  const { location, cars } = useSearchStore()

  return (
    <>
      <Header />
      <CarMainCategoriesPanel />
      <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6 mt-8 px-4 md:px-6">
        {cars && cars.length > 0 ? (
          cars.map((car) => (
            <div key={car.id} className="flex justify-center">
              <CarCard userCar={car} />
            </div>
          ))
        ) : (
          <p className="text-center text-gray-500 col-span-3">
            No cars available at the moment.
          </p>
        )}
      </div>
    </>
  );
};
