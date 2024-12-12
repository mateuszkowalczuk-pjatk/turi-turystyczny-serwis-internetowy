import { ReactNode } from 'react'
import PremiumTitle from '../PremiumTitle'
import PremiumPanel from '../PremiumPanel'
import styles from './PremiumContent.module.css'

const PremiumContent = ({ content }: { content: ReactNode }) => {
    return (
        <div className={styles.content}>
            <PremiumTitle />
            <PremiumPanel content={content} />
        </div>
    )
}

export default PremiumContent
