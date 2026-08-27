import Link from 'next/link'
import Head from 'next/head'
import React from 'react'
import { ModeToggle } from './mode-toggler'
import { ReadrepoIcon } from '../icons/ReadRepo'
import { BranchMark } from '../layout/app-shell'

const Navbar = () => {
  return (
    <div className='relative h-full flex-col overflow-hidden bg-background'>
        <div className='pointer-events-auto absolute insert-0'>
            <header className='relative z-10 flex h-14 items-center justify-baseline px-4'>
                <Link href="/">
                    <BranchMark/>
                </Link>
                {/* <ModeToggle/> */}
            </header>
        </div>
    </div>
  )
}

export default Navbar