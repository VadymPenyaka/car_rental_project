import { HeaderNavigation } from './HeaderNavigation'
import { HeaderInformation } from './HeaderInformation'
import { HeaderInformationMobile } from './HeaderInformationMobile'
import useAuthHeader from 'react-auth-kit/hooks/useAuthHeader'


export const Header = () => {
	const authHeader = useAuthHeader()
	console.log("Auth Header:", authHeader) 
	return (
		<div className='w-full h-16 shadow-md lg:h-36'>
			<div className="max-w-5xl mx-auto hidden lg:block">
				<HeaderInformation />
				<HeaderNavigation />
			</div>
			<div className='max-w-5xl mx-auto block lg:hidden'>
				<HeaderInformationMobile />
			</div>
		</div>
	)
}
