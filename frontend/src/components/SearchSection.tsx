import { Children } from 'react';

export const SearchSection = ({ children }: any) => {
	return (
		<>
			{Children.map(children, child => <div>{ child }</div>)}
		</>
	)
}