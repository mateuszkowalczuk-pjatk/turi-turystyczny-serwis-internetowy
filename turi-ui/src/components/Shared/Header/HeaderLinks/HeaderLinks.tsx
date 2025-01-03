import { ReactNode } from 'react'
import { useLocation } from 'react-router-dom'
import styles from './HeaderLinks.module.css'

interface Props {
    firstLink: ReactNode
    secondLink: ReactNode
    thirdLink: ReactNode
    fourthLink?: ReactNode
    isPremium?: boolean
}

const HeaderLinks = ({ firstLink, secondLink, thirdLink, fourthLink, isPremium }: Props) => {
    const location = useLocation()
    const isActive = (path: string) => location.pathname === path

    return (
        <div className={`${styles.links} ${isPremium ? styles.premium : ''}`}>
            <div className={`${styles.link} ${isActive('/reservations') ? styles.active : ''}`}>{firstLink}</div>
            <div className={`${styles.link} ${isActive('/stays') ? styles.active : ''}`}>{secondLink}</div>
            <div className={`${styles.link} ${isActive('/favourites') ? styles.active : ''}`}>{thirdLink}</div>
            {fourthLink && (
                <div className={`${styles.link} ${isActive('/tourism') ? styles.active : ''}`}>{fourthLink}</div>
            )}
        </div>
    )
}

export default HeaderLinks
