import { apiRequest } from "@/helpers/apiRequest"
import lvivPanorama from "../../assets/lvivPanorama.jpg"
import { Button } from "../ui/button"

export const Hero = () => {
	
	const handleButtonPress = async () => {
		let response = await apiRequest({
			baseURL: 'https://randomuser.me',
			path: 'api/',
			method: 'GET',
			headers: {
				'Content-Type': 'application/json'
			}
		})

		console.log(response);
		
	}

	return (
		<div className="bg-cover bg-center h-[535px]"
			style={{
				backgroundImage: `url(${lvivPanorama})`,
			}}>
			<h1>Автопрокат, якому довіряють!</h1>
			<p><span>4 / 5 ★ ★ ★ ★ ☆</span> за <a href="#">Google відгуками</a></p>
			<Button onClick={handleButtonPress}>Click Me!</Button>
		</div>
	)
}