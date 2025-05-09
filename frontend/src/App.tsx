import { CarMainCategoriesPanel } from "./components/CarMainCategoriesPanel/CarMainCategoriesPanel"
import { CirclePercSection } from "./components/CirclePercSection"
import { FeaturesCarousel } from "./components/FeaturesCarousel"
import { Header } from "./components/header/Header"
import Hero from "./components/Hero/Hero"
import { RentalInfo } from "./components/RentalInfo"

const App = () => {
	return (
		<>
			<Header />
			<Hero />
			<CarMainCategoriesPanel />
			<FeaturesCarousel />
			<CirclePercSection />
			<RentalInfo />
		</>
	)
}

export default App