import './App.css'
import { Header, Main, Footer, SearchSection } from './components/'

function App() {
	return (
		<>
			<Header />
			<Main>
				<SearchSection>
					<h1>Автопрокат, якому довіряють!</h1>
					{/* <Rating stars={4.5} />
					<SearchBar /> */}
				</SearchSection>
			</Main>
			<Footer>

			</Footer>
		</>
	)
}

export default App
