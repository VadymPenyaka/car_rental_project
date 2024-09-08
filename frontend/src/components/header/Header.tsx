import React from 'react'
import { HeaderNavigation } from './HeaderNavigation'
import { HeaderInformation } from './HeaderInformation'

export const Header = () => {
  return (
    <div className='w-full h-36 shadow-md'>
      <div className="max-w-5xl mx-auto">
        <HeaderInformation />
        <HeaderNavigation />
      </div>
    </div>
  )
}
