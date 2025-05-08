import { CarMainCategoriesPanel } from "./components/CarMainCategoriesPanel/CarMainCategoriesPanel"
import { CirclePercSection } from "./components/CirclePercSection"
import { FeaturesCarousel } from "./components/FeaturesCarousel"
import { Header } from "./components/header/Header"
import Hero from "./components/Hero/Hero"

const App = () => {
	return (
		<>
			<Header />
			<Hero />
			<CarMainCategoriesPanel />
			<FeaturesCarousel />
			<CirclePercSection />
		</>
	)
}

export default App