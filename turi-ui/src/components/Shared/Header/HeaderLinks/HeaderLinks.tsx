import { useHooks } from '../../../../hooks/shared/useHooks.ts'
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
    const { location } = useHooks()
    const isActive = (path: string) => location.pathname === path

    return (
        <div className={`${styles.links} ${isPremium ? styles.premium : ''}`}>
            <div className={`${styles.link} ${isActive('/reservations') ? styles.active : ''}`}>{firstLink}</div>
            <div className={`${styles.link} ${isActive('/realized') ? styles.active : ''}`}>{secondLink}</div>
            <div className={`${styles.link} ${isActive('/favourites') ? styles.active : ''}`}>{thirdLink}</div>
            {fourthLink && (
                <div className={`${styles.link} ${isActive('/tourism') ? styles.active : ''}`}>{fourthLink}</div>
            )}
        </div>
    )
}

export default HeaderLinks
