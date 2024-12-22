import { ReactNode } from 'react'
import styles from './FooterModule.module.css'

interface Props {
    title: ReactNode
    firstOption: ReactNode
    secondOption: ReactNode
    thirdOption?: ReactNode
    fourthOption?: ReactNode
    fifthOption?: ReactNode
}

const FooterModule = ({ title, firstOption, secondOption, thirdOption, fourthOption, fifthOption }: Props) => {
    return (
        <div className={styles.module}>
            {title}
            {firstOption}
            {secondOption}
            {thirdOption}
            {fourthOption}
            {fifthOption}
        </div>
    )
}

export default FooterModule
