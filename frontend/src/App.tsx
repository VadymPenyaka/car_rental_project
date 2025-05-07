import { CarMainCategoriesPanel } from "./components/CarMainCategoriesPanel/CarMainCategoriesPanel"
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
		</>
	)
}

export default App