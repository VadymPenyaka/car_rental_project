import Logo from '../ui/Logo'
import phone from '../../assets/smartphone.svg'
import viber from '../../assets/viber.png'
import telegram from '../../assets/telegram.png'
import whatsup from '../../assets/whatsup.png'
import ring_phone from '../../assets/phone_2.svg'
import account from '../../assets/account.svg'
import { AddresSelect } from './AddresSelect'
import { Button } from '../ui/button'
import { Link } from 'react-router'

export const HeaderInformation = () => {
	return (
		<div className='justify-between items-center mt-6 hidden lg:flex'>
			<Logo />
			<AddresSelect />
			<div className='flex items-center'>
				<Logo
					src={phone}
					width='14rem'
				/>
				<p>+38 (067) 521 78 77</p>
				<Logo
					src={viber}
					width='20rem'
				/>
				<Logo
					src={telegram}
					width='20rem'
				/>
				<Logo
					src={whatsup}
					width='20rem'
				/>
			</div>
			<div className='flex items-center'>
				<Logo
					src={ring_phone}
					width='20rem'
				/>
				+38 (044) 531 78 77
			</div>
			<div className="flex items-center">
				<Link to='/login'>
					<Button
						variant={'outline'}
					>
						<Logo
							src={account}
							width='20rem'
						/>
						Login
					</Button>
				</Link>
			</div>
		</div>
	)
}
