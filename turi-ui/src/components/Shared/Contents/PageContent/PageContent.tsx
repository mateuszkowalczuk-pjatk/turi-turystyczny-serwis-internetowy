import { ReactNode } from 'react'
import styles from './PageContent.module.css'

interface Props {
    title: ReactNode
    firstPanel: ReactNode
    secondPanel?: ReactNode
    thirdPanel?: ReactNode
    fourthPanel?: ReactNode
    fifthPanel?: ReactNode
}

const PageContent = ({ title, firstPanel, secondPanel, thirdPanel, fourthPanel, fifthPanel }: Props) => {
    return (
        <div className={styles.content}>
            {title}
            {firstPanel}
            {secondPanel}
            {thirdPanel}
            {fourthPanel}
            {fifthPanel}
        </div>
    )
}

export default PageContent
