import { ReactNode } from 'react'
import styles from './HeaderLinks.module.css'

interface Props {
    firstLink: ReactNode
    secondLink: ReactNode
    thirdLink: ReactNode
    fourthLink?: ReactNode
    isPremium?: boolean
}

const HeaderLinks = ({ firstLink, secondLink, thirdLink, fourthLink, isPremium }: Props) => {
    return (
        <div className={`${styles.links} ${isPremium ? styles.premium : ''}`}>
            {firstLink}
            {secondLink}
            {thirdLink}
            {fourthLink}
        </div>
    )
}

export default HeaderLinks
