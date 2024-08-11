import logo from '../../assets/logo.png' 

export const Logo = ({ width = 100, height = 100, alt = 'Company Logo', ...props }) => {
    return (
      <img
        src={logo}
        width={width}
        height={height}
        alt={alt}
        {...props}
        style={{ objectFit: 'contain', ...props.style }}
      />
    );
  };
  