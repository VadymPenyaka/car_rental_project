import { Children } from 'react';

export const Main = ({ children }: any) => {
	return (
		<>
			{Children.map(children, child => <div>{ child }</div>)}
		</>
	)
}