import mainLogo from '../../assets/mainLogo.png'

interface LogoProps {
	src?: string
	alt?: string
	width?: string
	height?: string
	className?: string
}

const Logo: React.FC<LogoProps> = ({
	src = mainLogo,
	alt = 'Company Logo',
	width = '100px',
	height = 'auto',
	className = '',
}) => {
	return (
		<div className={`flex items-center justify-center m-1 ${className}`}>
			<img
				src={src}
				alt={alt}
				width={width}
				height={height}
				className="object-contain"
			/>
		</div>
	);
};

export default Logo;
