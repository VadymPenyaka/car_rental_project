import { Header } from "../header/Header"
import { CarCard } from "../CarCard/CarCard"
import { CarMainCategoriesPanel } from "../CarMainCategoriesPanel/CarMainCategoriesPanel"
import { useSearchStore } from "@/stores/useSearchStore"
import { useEffect, useState } from "react";
import { set } from "date-fns";
import { sendSearchRequest } from "../CarRentalSearch/request";
import { userCar } from "@/interfaces/userCar";

export const SearchPage: React.FC = () => {
  const [cars, setCars] = useState<userCar[]>([])


  useEffect(() => {
    let initCars = async() => {
      const requestData = {}
      
      let response = await sendSearchRequest(requestData)
      setCars(response as userCar[])
    }
    initCars()
  }, [])

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
