import React from 'react';
import mainLogo from '../../assets/mainLogo.png'
// Define the props for the Logo component
interface LogoProps {
  // Optional: URL for the logo image
  src?: string;
  // Optional: Alt text for the logo image
  alt?: string;
  // Optional: Width of the logo
  width?: string;
  // Optional: Height of the logo
  height?: string;
  // Optional: Additional CSS classes for customization
  className?: string;
}

// Functional component with TypeScript
const Logo: React.FC<LogoProps> = ({
  src = mainLogo, // Default logo if none provided
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
